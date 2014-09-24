(defproject clj-workshop-examples "0.1.0-SNAPSHOT"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [clj-http "1.0.0"]
                 [clj-kafka "0.2.6-0.8"]
                 [com.runa/clj-hazelcast "1.0.1"]
                 [midje "1.6.3"]
                 [org.clojure/core.async "0.1.338.0-5c5012-alpha"]
                 [compojure "1.1.6"]
                 [ring/ring-jetty-adapter "1.2.0"]
                 [ring/ring-core "1.2.0"]
                 [ring/ring-json "0.1.2"]]
  :ring {:handler compojure-rest.handler/app}
  :main ^:skip-aot clj-hazelcast-example.core
  :target-path "target/%s"
  :plugins [[lein-midje "3.1.3"][lein-ring "0.8.10"]]
  :profiles {:main-hazelcast {:main clj-hazelcast-example.core}
             :main-kafka {:main kafka.core}}
   :aliases {"run-main-hazelcast" ["with-profile" "main-hazelcast" "run"]
             "run-main-kafka" ["with-profile" "main-kafka" "run"]}
  )
