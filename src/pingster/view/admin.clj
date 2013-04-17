(ns pingster.view.admin
  (:require [compojure.core :refer [defroutes GET]]
            [stencil.core :as stencil]
            [pingster.view.common :refer [restricted admin? wrap-layout]]
            [pingster.rest.scheduler :as scheduler]))

(defn- page-body []
  (stencil/render-file
   "pingster/view/templates/admin"
   {:elems (scheduler/get-schedules)}))

(defn- render-page [request]
  (wrap-layout "Admin"
               (page-body)))

(defroutes admin-routes
  (GET "/admin" request (restricted admin? render-page request)))
