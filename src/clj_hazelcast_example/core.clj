;; Hazelcast computations
;
;; what to do next?
; * cache values for another client
; * run ping-pong with other servers
; * rewrite ping-pong method to callbacks


(ns clj-hazelcast-example.core
  (:require [clj-hazelcast.core :as hzlc])
  (:gen-class))

(def test-map (atom nil))

(defn fixture []
  (hzlc/init)
  (reset! test-map (hzlc/get-map "this-is-a-map"))
  )

(def p (fixture))

(def events (atom []))

(defn listener-callback [& event]
  (swap! events conj event))

(defn ping-pong []
  (let [p (:ping-pong @test-map)]
    (println p)
    (if (nil? p)
      (hzlc/put! @test-map :ping-pong "ping")
      (if (= "ping" p)
        (hzlc/put! @test-map :ping-pong "pong")
        (hzlc/put! @test-map :ping-pong "ping")))
    )
  )

(defn -main
  [& args]

  (println "-> simple put to hazelcast")
  (hzlc/put! @test-map :foo 1)
  (println (:foo @test-map))

  (println "-> count events via listener")

  (def listener (hzlc/add-entry-listener! @test-map listener-callback))
  (hzlc/put! @test-map :baz "foobar")
  (hzlc/put! @test-map :foo "bizbang")
  (Thread/sleep 1000)

  (println (str "there are " (count @events) " events"))

  (hzlc/remove-entry-listener! @test-map listener)

  (while true
    (do
      (ping-pong)
      (Thread/sleep (rand-int 1000))))
  )
