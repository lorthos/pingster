(ns pingster.util.quartz
  (:require [clojurewerkz.quartzite.scheduler :as qs]
            [clojurewerkz.quartzite.triggers :as t]
            [clojurewerkz.quartzite.jobs :as j]
            [clojurewerkz.quartzite.conversion :as qc]
                        [org.httpkit.client :as http]
                        [postal.core :as postal]
                        [pingster.util.resources :as r]
            )
  (:use [clojurewerkz.quartzite.jobs :only [defjob]]
        [clojurewerkz.quartzite.schedule.calendar-interval :only [schedule with-interval-in-minutes]]))

(defn get-act [url] 
      (println "Start Http Get: " url)
      (let [{:keys [status headers body error] :as resp} @(http/get url)]
        (if (not= 200 status)
            (do 
              (println "Respone Code is not ok: " status)
              (postal/send-message ^{:host (r/props :email_host)
                             :user (r/props :user)
                             :pass (r/props :pass)
                             :ssl 
                             :yes!!!11}
                           {:from (r/props :from)
                            :to (r/props :to)
                            :subject (str "Pingster Alert For: " url)
                            :body body}))
            (println "HTTP GET success: " status))
        status)
)

(def options {:timeout 900})

(defjob HttpPingJob
  [ctx]
  (let [m (qc/from-job-data ctx)]
    ; (println ctx)
    ; (println m)
    (get-act (get m "url"))
    )
)


(defn create-new-schedule-on-quartz [id frequency address]
    (let [job (j/build
              (j/of-type HttpPingJob)
              (j/using-job-data {"url" address "id" id})
              (j/with-identity (j/key (str "jobs:" id))))
        trigger (t/build
                  (t/with-identity (t/key (str "triggers:" id)))
                  (t/start-now)
                  (t/with-schedule (schedule
                                     (with-interval-in-minutes (read-string frequency)))))]
  (qs/schedule job trigger))
    )

(defn delete-schedule-from-quartz [id] 
  (let [trigger-id (t/key (str "triggers:" id))]
     (qs/unschedule-job trigger-id)
  )
)