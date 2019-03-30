(ns roomkey.views.components.datepicker
  (:require
   [cljs-time.core :as time]
   [cljs-time.format :as f]
   [cljs-time.coerce :refer [from-date to-string from-string]]
   [goog.string :as gstring]
   [reagent.core :as r]
   [re-frame.core :refer [reg-event-db reg-event-fx
                          subscribe dispatch
                          debug]]))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Notes
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; I wrote this datepicker years ago. It should be cleaned up and put in a
;; separate library. As is, it's not ideal but I will use it as a quick and
;; dirty datepicker for expediency's sake.
;;==============================================================================
;; Date/Time Formats
;;==============================================================================
;; DateTime: goog.date.datetime instance
;; ISO-8601: date string in YYYY-MM-DD format
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;; I don't remember why I needed a numerical value.
(defn day-of-week-numerical
  "Given a datetime, return the value of the day of the week."
  [datetime]
  (let [day-of-week-table {"Sun" 0 "Mon" 1 "Tue" 2 "Wed" 3 "Thu" 4 "Fri" 5
                           "Sat" 6}]
    (get day-of-week-table (f/unparse (f/formatter "EEE") datetime))))

(defn parse-date-time
  "Parses yyyy-MM-dd'T'HH:mm:ss.SSSZZ into date-time."
  [date-time]
  (f/parse (:date-time f/formatters) date-time))

(defn first-day-of-month
  "Given a date-time, return the date-time of the first day in the month."
  [date-time]
  (time/date-time (time/year date-time)
                  (time/month date-time)
                  1))

(defn collect-dates
  "Generate datetimes for days in the month of datetime in date string format.
  Returns ((prev-month-dates-to-fill-up-empty-calendar-spots)
           (current-month-dates)
           (next-month-dates-to-fill-up-empty-calendar-spots)"
  [date-time]
  (let [first-day (first-day-of-month date-time)
        first-dow (day-of-week-numerical first-day)
        days-in-month (-> first-day
                          (time/plus (time/months 1))
                          (time/minus (time/days 1))
                          (time/day))
        initial-pad
        (->> (take first-dow
                   (iterate #(time/minus % (time/days 1))
                            (time/minus first-day (time/days 1))))
             (map to-string)
             reverse
             (map (fn [date]
                    {:date date
                     :month-type "prev-month"})))
        days-seq
        (for [day (take days-in-month (iterate inc 1))]
          (->> (to-string (time/date-time (time/year date-time)
                                          (time/month date-time)
                                          day))
               ((fn [date]
                  {:date date
                   :month-type "curr-month"}))))
        end-pad
        (for [day (take (- 7 (mod (count (concat initial-pad days-seq)) 7))
                        (iterate inc 1))]
          (let [year-month-dt (time/plus date-time (time/months 1))]
            (->> (to-string (time/date-time (time/year year-month-dt)
                                            (time/month year-month-dt)
                                            day))
                 ((fn [date]
                    {:date date
                     :month-type "next-month"})))))]
    (concat initial-pad days-seq end-pad)))

(defn generate-calendar-month
  "Takes a sequence of keys for where the data should be stored."
  [keyseq]
  (let [component-state (r/atom {:selected-date nil
                                 ;; goog.date.datetime
                                 :selected-month (time/now)})]
    (fn []
      (let [{:keys [selected-date selected-month]} @component-state
            selected-month-MMM (f/unparse (f/formatter "MMM")
                                          selected-month)
            selected-month-YYYY (f/unparse (f/formatter "YYYY")
                                           selected-month)]
        [:div {:class "datepicker"}
         [:table
          [:thead
           [:tr {:class "top"}
            [:th
             {:class "prev"
              :on-click
              #(swap!
                component-state assoc :selected-month
                (time/minus selected-month (time/months 1)))}
             "‹"]
            [:th
             {:class "switch"
              :col-span "5"}
             (str selected-month-MMM " / " selected-month-YYYY)]
            [:th
             {:class "next"
              :on-click
              #(swap!
                component-state assoc :selected-month
                (time/plus selected-month (time/months 1)))}
             "›"]]
           [:tr 
            [:th {:class "dow"} "Su"]
            [:th {:class "dow"} "Mo"]
            [:th {:class "dow"} "Tu"]
            [:th {:class "dow"} "We"]
            [:th {:class "dow"} "Th"]
            [:th {:class "dow"} "Fr"]
            [:th {:class "dow"} "Sa"]]
           (for [week (partition-all 7 (collect-dates selected-month))]
             ^{:key (str "datepicker-" keyseq "-" week)}
             [:tr
              (for [day week]
                ^{:key (str "datepicker-" keyseq "-" day)}
                [:td
                 {:id (:date day)
                  :class
                  (if (= selected-date (:date day))
                    (str "selected-date " "day " (:month-type day))
                    (str "day " (:month-type day)))
                  :data-date (:date day)
                  :on-click
                  #(swap! component-state assoc :selected-date (:date day))}
                 (time/day (parse-date-time (:date day)))])])]]]))))

(defn datepicker
  "Start datepicker on today's date.
  Store data at keyseq (ex. [:state :calendar]) in re-frame DB.
  handler takes a function that runs when a day is clicked."
  [keyseq]
  [generate-calendar-month keyseq])

