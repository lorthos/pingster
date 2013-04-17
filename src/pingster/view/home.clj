(ns pingster.view.home
  (:require [compojure.core :refer [defroutes GET]]
            [stencil.core :as stencil]
            [pingster.view.common :as common]))

(defn- page-body []
  (stencil/render-file
   "pingster/view/templates/home"
   {}))

(defn- render-page [request]
  (common/wrap-layout "Home"
                      (page-body)))

(defroutes home-routes
  (GET "/" request (render-page request)))
