(ns pingster.server
  (:require [org.httpkit.server :as httpkit]
            [pingster.app :as app]
            [pingster.util.quartz :as q]
            [clojurewerkz.quartzite.scheduler :as qs]
            [monger.collection :as mc])
  (:gen-class))

(defn- add-port [options port]
  (let [port (if port
               (Integer/parseInt port)
               8080)]
    (assoc options :port port)))

(defn- parse-args [[port :as args]]
  (-> {}
      (add-port port)))

(defn -main [& args]
  (println "Starting server...")
  (let [options (parse-args args)
        port (:port options)
        server (httpkit/run-server (var app/site-handler)
                                {:port port :join? false})]
    (println "Server started on port" port)
    (println (str "You can view the site at http://localhost:" port))
    (println "Starting quartz...")
    (qs/initialize)
    (qs/start)
    (println "Started quartz...")
    (println "Initializing existing schedules...")
    ;initialize existing triggers on startup
    (doseq [trigger (mc/find-maps "triggers")]
      (println "creating persisted schedule" (:_id trigger))
      (q/create-new-schedule-on-quartz (:_id trigger) (str (:frequency trigger)) (:address trigger)))
    (println "Initialized existing schedules...")
    server))

(defn stop [instance]
  (.stop instance)
  (println "Server stopped"))
