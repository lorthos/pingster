(ns pingster.app
  (:require [clojure.core.cache :as cache]
            [compojure.core :refer [defroutes routes]]
            [compojure.handler :as handler]
            [compojure.route :as route]
            [stencil.loader :as stencil]
            [clojure.core.cache :as cache]
            [pingster.middleware.session :as session-manager]
            [pingster.middleware.context :as context-manager]))

;; Initialization
; Add required code here (database, etc.)
(stencil/set-cache (cache/ttl-cache-factory {}))
;(stencil/set-cache (cache/lru-cache-factory {}))


;; Load public routes
(require '[pingster.view.home :refer [home-routes]]
         '[pingster.rest.scheduler :refer [scheduler-routes]])

;; Load authentication routes
(require '[pingster.view.auth :refer [auth-routes]])

;; Load private routes
(require
         '[pingster.view.admin :refer [admin-routes]])


;; Ring handler definition
(defroutes site-handler
  (-> (routes home-routes
              scheduler-routes
              auth-routes
              admin-routes
              (route/resources "/")
              (route/not-found "<h1>Page not found.</h1>"))
      (session-manager/wrap-session)
      (context-manager/wrap-context-root)
      (handler/site)))
