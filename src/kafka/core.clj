;; Read from Kafka
;
; * core.async
; * go routines
;
;; what to do next?
;
; * rewrite the code to use core.async channels
; * wait for messages on topic
; * proxy to another topic


(ns kafka.core
  (:require
        [clojure.core.async :as async]
        [clj-kafka.core :refer [with-resource]]
        [clj-kafka.producer :refer [producer send-message message]]
        [clj-kafka.consumer.zk :refer [consumer messages shutdown]])
  (:gen-class))

(def p (producer {"metadata.broker.list" "localhost:9092"
                  "serializer.class" "kafka.serializer.DefaultEncoder"
                  "partitioner.class" "kafka.producer.DefaultPartitioner"}))

(def config {"zookeeper.connect" "localhost:2181"
             "group.id" "clj-kafka.consumer"
             "auto.offset.reset" "largest"
             "auto.commit.enable" "false"})

(defn kafka-send []
  (println "sending")
  (send-message p (message "test" (.getBytes "this is my message"))))

(defn kafka-receive []
  (with-resource [c (consumer config)]
    shutdown
    (doseq [message (messages c "test")]
        (println "received"))))

(defn -main
  [& args]
  (let [chn (async/chan)]
    (async/thread (kafka-receive))
    (while true
        (Thread/sleep 500)
        (kafka-send))))
