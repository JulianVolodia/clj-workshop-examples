(ns compojure-rest.handler
  (:use compojure.core)
  (:require [compojure.handler :as handler]
            [compojure.route :as route]
            [ring.middleware.json :as middleware]
            [ring.util.response :refer [resource-response response status redirect]]
            [ring.adapter.jetty :refer (run-jetty)])
  (:gen-class))

(def messages (atom []))

(defn get-messages []
  (deref messages))

(defn put-message [message]
  (swap! messages conj message))

(defroutes app-routes
  (GET "/" []
    (redirect "messages"))

  (GET "/messages" []
    (response (get-messages)))

  (POST "/messages" {message :body}
    (put-message message)
    (status (response "") 201))

  (route/resources "/")
  (route/not-found "Not Found"))

(def app
  (-> (handler/api app-routes)
      (middleware/wrap-json-body {:keywords? true})
      (middleware/wrap-json-response)))

(defn -main [& args]
  (run-jetty app {:port 3000 :join? false }))
