Aby umożliwić  zbieranie metryki 'arbitrage-value' należy mieć zainstalowanego Dockera.
Wykonać następujące polecenia:
- docker network create monitoring
- docker run --name=prometheus -d --network=monitoring -p 9090:9090 -v <Pełna ścieżka do pliku>prometheus.yml:/etc/prometheus/prometheus.yml prom/prometheus
- docker run -d --name=grafana --network=monitoring -p 3000:3000 grafana/grafana
Następnie wejść na adres localhost:3000, zalogować się 'admin/admin' i dodać nowy Data Source Prometheus (http://prometheus:9090)
W zakładce 'Explore' wyszukać metrykę
Można również utworzyć Dashboard lub zaimportować go z pliku 'grafana.json'
