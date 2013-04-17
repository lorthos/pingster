(ns pingster.util.resources-test
    (:use [clojure.test])
    (:require [pingster.util.resources :as resources])
    )


(deftest properties-test
  (testing "test that property file can be loaded"
    (is (= "@gmail.com" (resources/props :user)))
    ))

