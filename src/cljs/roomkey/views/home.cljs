(ns roomkey.views.home
  (:require
   [dommy.core :as dom :refer-macros [sel1]]
   [reagent.core :as r]
   [re-frame.core :refer [dispatch subscribe]]
   [roomkey.views.components.top-nav :refer [top-nav]]
   [roomkey.views.components.footer :refer [footer]]
   [roomkey.handlers.home]
   [roomkey.subs.home]
   [roomkey.subs.util]
   [roomkey.views.components.datepicker :refer [datepicker]]
   ["react" :as react]
   ["react-dates/initialize"]
   ["react-dates" :as react-dates]))

(def DateRangePicker
  "Helper that lets us use the component in hiccup form: [DateRangePicker]."
  (r/adapt-react-class react-dates/DateRangePicker))

(defn guests-rooms-dropdown []
  (let [guest-quantity (subscribe [:home/guest-quantity])
        room-quantity (subscribe [:home/room-quantity])]
    [:div {:class ["absolute" "bg-white"]
           :id "guests-rooms-dropdown"}
     [:div
      [:label "Guests"]
      [:div
       [:button
        {:class ["bg-white" "border"]
         :on-click
         #(dispatch [:home/set-guest-quantity (dec @guest-quantity)])}
        "-"]
       [:span @guest-quantity]
       [:input {:class ["hidden"]}]
       [:button
        {:class ["bg-white" "border"]
         :on-click
         #(dispatch [:home/set-guest-quantity (inc @guest-quantity)])}
        "+"]]]
     [:div
      [:label "Rooms"]
      [:div
       [:button
        {:class ["bg-white" "border"]
         :on-click
         #(dispatch [:home/set-room-quantity (dec @room-quantity)])}
        "-"]
       [:span @room-quantity]
       [:input {:class ["hidden"]}]
       [:button {:class ["bg-white" "border"]
                 :on-click
                 #(dispatch [:home/set-room-quantity (inc @room-quantity)])}
        "+"]]]]))

(defn search-location []
  (let [autofill-locations (subscribe [:home/autofill-locations])]
    [:div {:class ["relative"]}
     [:input
      {:class ["p-2" "mr-2" "h-full"]
       :id "location-search"
       :placeholder "Where are you going?"
       :on-key-up
       (fn [e]
         (let [target (.-target e) 
               value (.-value target)]
           (if (>= (count value) 3)
             (dispatch [:home/autofill-location value])
             (dispatch [:home/set-autofill-locations nil]))))}]
     [:div {:class ["absolute"]
            :id "location-search-dropdown"}
      [:ul {:class ["bg-white" "list-reset"]}
       (for [location @autofill-locations]
         ^{:key (:id location)}
         [:li (:name location)])]]]))

(defn search []
  (let [guest-quantity (subscribe [:home/guest-quantity])
        room-quantity (subscribe [:home/room-quantity])
        check-in-date (subscribe [:home/check-in-date])
        check-out-date (subscribe [:home/check-out-date])
        date-focused-input (subscribe [:home/date-focused-input])]
    [:div {:class ["flex" "text-lg"]}
     [:div {:class ["bg-white" "flex"]}
      [search-location]
      [:div {:class "relative"}
       [DateRangePicker
        {:start-date @check-in-date
         :start-date-id "hello"
         :end-date @check-out-date
         :end-date-id "bye"
         :on-dates-change
         #(let [{:keys [startDate endDate]} (js->clj % :keywordize-keys true)]
            (dispatch [:util/dispatch-n
                       [[:home/set-check-in-date startDate]
                        [:home/set-check-out-date endDate]]]))
         :focused-input @date-focused-input
         :on-focus-change #(dispatch [:home/set-date-focused-input %])
         :start-date-placeholder-text "Check-in"
         :end-date-placeholder-text "Check-out"}]]
      [:div {:class ["relative"]}
       [:label {:id "guests-rooms"
                :class ["h-full" "inline-flex" "items-center"]
                :for "guests-rooms-toggle"}
        [:span {:class ["p-2" "block"]}
         (str @guest-quantity
              (if (= @guest-quantity 1)
                " Guest"
                " Guests "))
         (str @room-quantity
              (if (= @room-quantity 1)
                " Room"
                " Rooms"))]]
       [:input {:type "checkbox"
                :class ["hidden"]
                :id "guests-rooms-toggle"}]
       [guests-rooms-dropdown]]]
     [:button {:class ["bg-blue" "hover:bg-blue-dark" "text-white" "font-bold"
                       "p-2" "rounded"]}
      "Search"]]))

(defn hero []
  [:div {:class ["hero" "flex" "flex-col" "items-center" "justify-center"
                 "bg-center" "bg-cover"]}
   [:div {:class ["bg-dark-blue-trans" "p-16"]}
    [:h1 {:class ["text-white"]}
     "Search hotels, Book Direct. Save."]
    [search]]])

(defn social-proof []
  [:div {:class ["flex" "justify-center"]}
   [:img {:class ["p-8"]
          :src (str "https://d75rs6siber1y.cloudfront.net/ea590c2680/apps/"
                    "careers/img/logos-desktop.svg")}]])

(defn misc
  "These should be reorganized."
  []
  [:div {:class ["flex"]}
   [:div {:class ["p-4"]}
    [:h3 "Set Price Alerts"]
    [:p "Create Price Alerts to monitor rates at your favorite hotels, and 
we’ll watch for lower prices for your trip. Book at the best time and relax, 
knowing you’ve got the best deal."]]
   [:div {:class ["p-4"]}
    [:h3 "Sign up to receive updates on exclusive news and special deals"]
    [:div {:class ["flex"]}
     [:input {:class ["inline-block"]
              :placeholder "Email"}]
     [:button {:class ["bg-blue" "hover:bg-blue-dark" "text-white" "font-bold"
                       "py-2" "rounded"]}
      "Sign Up"]]]
   [:div {:class ["p-4"]}
    [:h3 "Take Room Key With You!"]
    [:p "Use Room Key’s Scout to get access to direct rates not offered on 
popular travel sites! Scout unlocks loyalty rates for your searches that other 
travel sites can’t."]
    [:img {:src (str "https://d75rs6siber1y.cloudfront.net/ea590c2680/apps/"
                     "roomkey/home/footer-tiles/img/scout-logo.svg")}]]])

(defn home []
  [:div
   [top-nav]
   [hero]
   [social-proof]
   [misc]
   [footer]]) 

(defn home-page []
  (r/create-class
   {:display-name "home-page"
    :reagent-render home}))
