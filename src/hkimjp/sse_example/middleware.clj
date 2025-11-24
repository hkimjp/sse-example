(ns hkimjp.sse-example.middleware
  (:require
   [hiccup2.core :as h]
   [ring.util.response :as resp]))

(defn wrap-hx [handler]
  (fn [req]
    (-> (handler req)
        h/html
        str
        resp/response
        (resp/content-type "text/html"))))
