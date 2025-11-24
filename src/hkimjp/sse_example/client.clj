(ns hkimjp.sse-example.client
  (:require
   [hkimjp.sse-example.views :refer [page]]))

(defn client [_request]
  (page
   [:div.m-4
    [:div.py-2
     [:button.bg-sky-300.hover:bg-blue-600.active:bg-red-400.text-white.px-2
      {:hx-get    "/hx/now"
       :hx-target "#now"
       :hx-swap   "innerHTML"}
      "What time?"]]
    [:div [:span#now.border-1 "..."]]
    [:div.text-2xl.font-bold.py-2 "SSE example, using htmx-ext-sse"]
    [:div
     {:hx-ext      "sse"
      :sse-connect "/event"
      :sse-swap    "message"}
     "Contents of this box will be updated in real time
    with every SSE message received from the chatroom."]]))

