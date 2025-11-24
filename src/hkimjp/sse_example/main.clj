(ns hkimjp.sse-example.main
  (:require
   [org.httpkit.server :as hk]
   [hkimjp.sse-example.routes :refer [handler]]))

(def server (atom nil))

(defn start-server [& _]
  (reset! server (hk/run-server #'handler {:port 8888})))

; stop server
; (@server)
