(ns aleph-example.core
  (:require [aleph.tcp :as tcp]
            [lamina.core :as l]
            [gloss.core :as g]
            [gloss.io :as gio]))

(defn print-name [name]
  (println "Name ->" name))

(defn print-age [age]
  (println "Age ->" age))

(defn client-process [ch client-info]
  (l/enqueue ch "#### What's your name?")
  (l/run-pipeline 
    (l/read-channel ch)
    (fn [received]
      (print-name received)
      (l/enqueue ch "#### What's your age?")
      (l/read-channel ch))
    (fn [received]
      (print-age received)
      (l/enqueue ch "OK, Thank you!")
      (l/close ch))))
      

(defn start-server [port]
  (tcp/start-tcp-server client-process 
                        {:port port, 
                         :frame (g/string :utf-8 :delimiters ["\n"])}))
;;;;;;;
;; usage
;; - on repl
;;  user=> (use 'aleph-example.core)
;;  user=> (start-server 8888)
;;
;; - on terminal
;; # telnet localhost 8888
;;