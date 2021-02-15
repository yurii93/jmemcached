## Сервис кэширования данных - JMemcached

Сервис JMemcached является многопоточным автономным сервисом, позволяющим взаимодействовать по сети со всеми его клиентами. Данный сервис хранит данные на основе хеш карты и позволяется выполнять операции добавления, получения и удаления любого значения по ключу, а также операции очистки всей карты. При добавлении значения по ключу возможно указать время жизни данного объекта и по истечении данного времени объект будет автоматически удален из карты хранения данных.