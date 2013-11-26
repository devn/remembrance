(ns remembrance.models.document
  (require [remembrance.db :as db]
           [ring.util.response :refer [response redirect-after-post status]]
           [taoensso.timbre :refer [info]]))

(def table-name "documents")

(def document-fields [:title
                      :body
                      :original_url
                      :original_html
                      :date_published
                      :date_ingested
                      :created_at
                      :updated_at
                      :ingest_state
                      :read])

(def validate-presence-of [:original_url])

(def field-defaults {:read false
                     :ingest_state "new"})


(defn all-documents []
  (db/select-all "documents"))

(defn every-valid-attribute? [model-attributes attributes]
  (every? #(some #{%} model-attributes) attributes))

(defn fail [error]
  (info error)
  error)

(defn pluck [s key]
  (map (fn [m] (get m key)) s))

(defn create-document [attrs]
  (let [query-response (db/insert table-name attrs)
        resp (query-response :response)]
    (info resp)
    (if (every? zero? (pluck resp :errors))
      (str "OK. " (first (pluck resp :generated_keys)))
      (str "Unprocessable entity."))))
