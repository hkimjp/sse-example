(ns hkimjp.sse-example.client
  (:require
   [hiccup2.core :as h]
   [ring.util.anti-forgery :refer [anti-forgery-field]]
   [hkimjp.sse-example.views :refer [page]]))

(defn client [_request]
  (page
   [:div.m-4
    #_[:div.py-2.flex.gap-x-4
       [:button.bg-sky-300.hover:bg-blue-600.active:bg-red-400.text-white.px-2
        {:hx-get    "/hx/now"
         :hx-target "#now"
         :hx-swap   "innerHTML"}
        "What time?"]
       [:div [:span#now.border-1 "..."]]]
    [:div.text-2xl.font-bold.py-2 "SSE example, using htmx-ext-sse"]
    [:form.flex.gap-x-4
     (h/raw (anti-forgery-field))
     [:input.border-1 {:name "message"}]
     [:button.bg-sky-400.rounded.hover:bg-sky-600.text-white
      {:hx-post "/broadcast" :hx-swap "none"}
      "broadcast"]]
    [:div.flex.gap-x-4
     [:div "broadcast ->"]
     [:div {:hx-ext      "sse"
            :sse-connect "/event"
            :sse-swap    "message"}
      "Contents of this box will be updated in real time
      with every SSE message received from the chatroom."]]]))

