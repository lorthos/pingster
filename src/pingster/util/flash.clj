(ns pingster.util.flash
  (:refer-clojure :exclude [get])
  (:require [pingster.middleware.session :as session-manager]))

(defn put!
  "Put key/value in flash"
  [k v]
  (session-manager/flash-put! k v))

(defn get
  "Retrieve a flash value"
  [k]
  (session-manager/flash-get k))
