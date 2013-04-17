(ns pingster.rest.scheduler
  (:require [compojure.core :refer [defroutes GET POST DELETE]]
            [stencil.core :as stencil]
            [pingster.view.common :as common]
            [pingster.util.quartz :as q]
            [clj-json.core :as json]
            [monger.core :as mg]
            [monger.collection :as mc]
            )
  (:use [clojurewerkz.quartzite.jobs :only [defjob]]
        [clojurewerkz.quartzite.schedule.calendar-interval :only [schedule with-interval-in-minutes]])
  (:import [org.bson.types ObjectId]))

(defn init-mongo []
  (let [uri (get (System/getenv) "MONGOLAB_URI" "mongodb://127.0.0.1/pingster")]
    (mg/connect-via-uri! uri))

  (mg/set-db! (mg/get-db "pingster"))
  )

(defn json-response [data & [status]]
  {:status (or status 200)
   :headers {"Content-Type" "application/json"}
   :body (json/generate-string data)})


(defn create-new-schedule [id frequency address]
  (mc/insert "triggers" { :_id id :frequency (read-string frequency) :address address})
  (q/create-new-schedule-on-quartz id frequency address)
  )

(defn delete-schedule [id] 
  (let []
     (q/delete-schedule-from-quartz id)
     (mc/remove "triggers" { :_id id })
  )
)

(defn get-schedules "gets scheduldes from mongo" [] 
  (mc/find-maps "triggers"))


(defroutes scheduler-routes
  (GET "/schedules" request (json-response (get-schedules)))
  (POST "/schedules" {params :params} (json-response (let [uuid (ObjectId.)
                                                           id (.toString uuid)]
                                                       (create-new-schedule id (params :frequency) (params :address) ))))
  (GET "/schedule/:id" [id]  (json-response (mc/find-one "triggers" { :_id id })))
  (DELETE "/schedule/:id" [id]  (let [result "done"]
                                  (delete-schedule id)
                                  (str result)))
  )
