(ns pingster.util.quartz-test
    (:use [clojure.test])
    (:require [pingster.util.quartz :as quartz])
    )

(deftest get-act-test
  (testing "http get"
    (is (= 200 (quartz/get-act "http://is.arazilla.com/")))
    ))
