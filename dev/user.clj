(ns user
  (:require
   [hkimjp.sse-example.main :as main]))

(main/start-server)
(println "http-kit started at port 8888")
