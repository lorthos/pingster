(ns pingster.app-test
    (:use [clojure.test])
    
    )

;(:require (fleetdb [embedded :as embedded] [file :as file]))

;;(require '(fleetdb [embedded :as embedded]))

; (deftest a-test
;   (testing "FIXME, I fail."
;     (let [db-atom (embedded/init-persistent "/tmp/fleetdb-demo.fdb")]
;   (embedded/query db-atom
;     ["insert" "records" [{"id" 1 "name" "foo"} {"id" 2 "name"
; "bar"}]])
;   (embedded/close db-atom))
;     ))

; (deftest b-test
;   (testing "FIXME, I fail."
; (let [db-atom (embedded/load-persistent "/tmp/fleetdb-demo.fdb")]
;   (prn (embedded/query db-atom
;          ["select" "records"]))
;   (embedded/close db-atom))
;     ))

