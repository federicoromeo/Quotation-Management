# Quotation-Management

Project of "Tecnologie informatiche per il WEB" .


Versione pure HTML

Un’applicazione web consente la gestione di richieste di preventivi per prodotti personalizzati. Un preventivo è
associato a un prodotto, al cliente che l’ha richiesto e all’impiegato che l’ha gestito. Il preventivo comprende una
o più opzioni per il prodotto a cui è associato, che devono essere tra quelle disponibili per il prodotto. Un
prodotto ha un codice, un’immagine e un nome. Un’opzione ha un codice, un tipo (“normale”, “in offerta”) e un
nome. Un preventivo ha un prezzo, definito dall’impiegato. Quando l’utente (cliente o impiegato) accede
all’applicazione, appare una LOGIN PAGE, mediante la quale l’utente si autentica con username e password.
Quando un cliente fa login, accede a una pagina HOME PAGE CLIENTE che contiene una form per creare un
preventivo e l’elenco dei preventivi creati dal cliente. Mediante la form l’utente per prima cosa sceglie il prodotto;
scelto il prodotto, la form mostra le opzioni di quel prodotto. L’utente sceglie le opzioni (almeno una) e conferma
l’invio del preventivo mediante il bottone INVIA PREVENTIVO. Quando un impiegato fa login, accede a una pagina
HOME PAGE IMPIEGATO che contiene l’elenco dei preventivi gestiti da lui in precedenza e quello dei preventivi
non ancora associati a nessun impiegato. Quando l’impiegato seleziona un elemento dall’elenco dei preventivi
non ancora associati a nessuno, compare una pagina PREZZA PREVENTIVO che mostra i dati del cliente (username)
e del preventivo e una form per inserire il prezzo del preventivo. Quando l’impiegato inserisce il prezzo e invia i
dati con il bottone INVIA PREZZO, compare di nuovo la pagina HOME PAGE IMPIEGATO con gli elenchi dei
preventivi aggiornati.
