(ns pingster.view.auth
  (:require [ring.util.response :as response]
            [compojure.core :refer [defroutes GET POST]]
            [stencil.core :as stencil]
            [pingster.util.session :as session]
            [pingster.view.common :refer [wrap-context-root get-context-root wrap-layout]]
            [pingster.util.resources :as r]))

(defn init-test-data
  "Initialise session with dummy data"
  []
  (session/set-user! {:login "admin"
                      :type :admin}))

(defn- login-page-body [request]
  (stencil/render-file
   "pingster/view/templates/auth"
   {:context-root (get-context-root)}))

(defn- login-page [request]
  (wrap-layout "Log in"
               (login-page-body request)))

(defn- login [params]
  (if (and (= (r/props :login_user) (:login params)) (= (r/props :login_pass) (:password params)))
    (init-test-data)
    (println "only user asd is allowed"))
  (response/redirect (wrap-context-root "/")))

(defn- logout [request]
  (session/logout)
  (response/redirect (wrap-context-root "/")))

(defroutes auth-routes
  (GET "/login" request (login-page request))
  (POST "/login" {params :params} (login params))
  (GET "/logout" request (logout request)))
