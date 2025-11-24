(ns hkimjp.sse-example.views
  (:require
   [hiccup2.core :as h]
   [ring.util.response :as resp]
   [taoensso.telemere :as t]))

(def version "0.1.0")

(def ^:private menu "text-xl font-medium text-white px-1 hover:bg-orange-500")

(def navbar
  [:div.flex.bg-orange-600.items-baseline.gap-x-4
   [:div.text-2xl.font-medium.text-white "SSE example"]
   #_[:div {:class menu} [:a {:href "/workspace"}  "workspace"]]
   #_[:div {:class menu} [:a {:href "/scoreboard"} "scoreboard"]]
   #_[:div {:class menu} [:a {:href "/logout"}     "logout"]]
   #_[:div {:class menu} [:a {:href "/help"}       "HELP"]]
   #_[:div {:class menu} [:a {:href "/admin"}      "admin"]]])

(def footer
  [:div.text-base
   [:hr]
   "hkimura " version])

(defn- base
  [content]
  [:html {:lang "en"}
   [:head
    [:meta {:charset "UTF-8"}]
    [:meta {:name "viewport"
            :content "width=device-width, initial-scale=1"}]
    [:link {:type "text/css"
            :rel  "stylesheet"
            :href "/assets/css/output.css"}]
    [:link {:rel "icon"
            :type "image/x-icon"
            :href "/favicon.ico"}]
    [:script {:type "text/javascript"
              :src  "/assets/js/htmx.min.js"
              :defer true}]
    [:script {:type "text/javascript"
              :src  "/assets/js/htmx-ext-sse.js"
              :defer true}]
    [:title "SSE example"]]
   [:body {:hx-boost "true"}
    [:div
     navbar
     content
     footer]]])

(defn page
  [content]
  (t/log! :debug (str "page"))
  (-> (str (h/html (h/raw "<!DOCTYPE html>") (base content)))
      resp/response
      (resp/header "Content-Type" "text/html")))

(defn error-page
  [content]
  (t/log! :debug (str "error-page" content))
  (-> (str (h/html (h/raw "<!DOCTYPE html>")
                   (base [:div
                          [:h1.text-red-600 "Error"]
                          content])))
      resp/response
      (resp/header "Content-Type" "text/html")))

