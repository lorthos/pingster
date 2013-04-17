(defproject pingster "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [http-kit "2.0.0"]
                 [compojure "1.1.3"]
                 [stencil "0.3.2"]
                 [clj-json "0.3.2"]
                 [clojurewerkz/quartzite "1.0.1"]
                 [com.novemberain/monger "1.5.0"]
                 [com.draines/postal "1.10.2"]
                 ]
  :plugins [[lein-localrepo "0.4.1"]
            [lein-deps-tree "0.1.2"]]
  :aot [pingster]
  :war-resources-path "resources/public"
  :main pingster.server)
