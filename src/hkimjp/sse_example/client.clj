(ns hkimjp.sse-example.client
  (:require
   [hkimjp.sse-example.views :refer [page]]))

(defn client [_request]
  (page
   [:div.m-4
    [:div.py-2
     [:button.bg-sky-500.hover:bg-sky-200.active:bg-red-400.text-white.px-2
      {:hx-get    "/hx/now"
       :hx-target "#now"
       :hx-swap   "innerHTML"}
      "What time?"]]
    [:div [:span#now.border-1 "..."]]
    [:div.font-2xl.text-bold.py-2 "SSE, 本番はここから"]
    [:div
     {:hx-ext      "sse"
      :sse-connect "/event"
      :sse-swap    "message"}
     "Contents of this box will be updated in real time
    with every SSE message received from the chatroom."]]))

