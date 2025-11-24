(ns hkimjp.sse-example.routes
  (:require
   [hiccup2.core :as h]
   [reitit.ring :as rr]
   [ring.middleware.defaults :refer [wrap-defaults site-defaults]]
   [ring.util.anti-forgery :refer [anti-forgery-field]]
   ; [ring.util.response :as resp]
   [taoensso.telemere :as tel]
   [hkimjp.sse-example.client :refer [client]]
   [hkimjp.sse-example.event :refer [event broadcast!]]
   [hkimjp.sse-example.middleware :refer [wrap-hx]]
   [hkimjp.sse-example.views :refer [page]]))

(defn hello [_]
  {:status 200
   :headers {"Content-Type" "text/html"}
   :body "<p>Hello, world.</p>"})

(defn get-example [_]
  (page
   [:div.m-4
    [:div.text-2xl.font-bold "Get"]
    [:div "you have clicked get"]
    [:div [:a.hover:underline {:href "/"} "back"]]]))

(defn form-example [_]
  (page
   [:div.m-4
    [:form {:method "post" :action "/post"}
     (h/raw (anti-forgery-field))
     [:input.border-1 {:name "name" :placeholder "what your name?"}]
     [:button.bg-sky-500.hover:bg-sky-200.active:bg-red-400.text-white
      "greet"]]]))

(defn post-example [{{:keys [name]} :params :as request}]
  (tel/log! {:level :info :id "post"
             :data (-> (:params request)
                       (dissoc :__anti-forgery-token))})
  (page
   [:div.m-4
    [:div (str "Nice to meet you, " name)]
    [:div [:a.hover:underline {:href "/"} "back"]]]))

(defn index [_]
  (page
   [:div.m-4
    [:div.text-2xl.font-bold "SSE-example"]
    [:ul
     [:li [:a.hover:underline {:href "/get"} "get-example"]]
     [:li [:a.hover:underline {:href "/post"} "post-example"]]
     [:li [:a.hover:underline {:href "/client"} "client"]]]]))

(def handler
  (rr/ring-handler
   (rr/router
    [["/" {:get {:handler index}}]
     ["/hello" hello]
     ["/event"  event]
     ["/broadcast" broadcast!]
     ["/client" client]
     ["/get" {:get {:handler get-example}}]
     ["/post" {:get  {:handler form-example}
               :post {:handler post-example}}]
     ["/hx" {:middleware [wrap-hx]}
      ["/now" {:get {:handler (fn [_] (java.util.Date.))}}]]])
   (rr/routes
    (rr/create-resource-handler {:path "/"})
    (rr/create-default-handler))
   {:middleware [[wrap-defaults site-defaults]]}))
