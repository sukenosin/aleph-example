
(ns aleph-example.gloss
  (:require [gloss.core :as g]
            [gloss.io :as gio]))

;; you can define a codec like "the structure of C language".
(g/defcodec my-codec {:a :int32,
                      :b (g/string :utf-8 :delimiters ["\0"]), 
                      :c :ubyte})

;; encode
(def encoded (gio/encode my-codec {:a 12345, 
                                   :b "foo bar baz",
                                   :c 111}))
;; print encoded bytes as vector
(vec (.array (gio/contiguous encoded)))


;; decode
(gio/decode my-codec encoded)