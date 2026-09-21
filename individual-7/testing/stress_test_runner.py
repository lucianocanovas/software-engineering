#!/usr/bin/env python3
"""
=========================================================================================
SCRIPT AUTOMATIZADO: stress_test_runner.py
=========================================================================================
Herramienta de pruebas de Carga y Stress para el sistema de Club Deportivo.
Ejecuta solicitudes HTTP concurrentes simulando picos de demanda en molinetes y cobranzas.

Características:
- Implementado con la librería estándar de Python (sin dependencias externas).
- Manejo de sesiones y cookies para autenticación en Spring Security.
- Extracción automática de tokens CSRF.
- Métricas estadísticas de latencia: Mínimo, Máximo, Promedio, Percentil 50, 90, 95 y 99.
- Detección de tasa de errores (HTTP 5xx / timeouts / fallas de conexión).
"""

import sys
import time
import re
import statistics
import urllib.request
import urllib.parse
import http.cookiejar
from concurrent.futures import ThreadPoolExecutor, as_completed

BASE_URL = "http://localhost:8080"
TOTAL_REQUESTS = 200
CONCURRENCY = 20

def login_and_get_cookie():
    """Realiza el login inicial y retorna una cookie jar con la sesión activa de Spring Security."""
    cj = http.cookiejar.CookieJar()
    opener = urllib.request.build_opener(urllib.request.HTTPCookieProcessor(cj))

    try:
        # 1. GET /login para extraer token CSRF
        resp = opener.open(f"{BASE_URL}/login")
        html = resp.read().decode('utf-8')
        match = re.search(r'name="_csrf"\s+value="([^"]+)"', html)
        csrf = match.group(1) if match else ""

        # 2. POST /login con credenciales de operador
        login_data = urllib.parse.urlencode({
            'username': 'operador',
            'password': 'operador123',
            '_csrf': csrf
        }).encode('utf-8')

        opener.open(f"{BASE_URL}/login", data=login_data)
        return opener
    except Exception as e:
        print(f"[!] Error en autenticación inicial: {e}")
        return None

def execute_request(opener, req_id):
    """Ejecuta una petición de consulta o verificación simulando carga."""
    start = time.perf_counter()
    try:
        # Petición a la consola de accesos o listado de socios
        resp = opener.open(f"{BASE_URL}/accesos", timeout=10)
        elapsed_ms = (time.perf_counter() - start) * 1000
        return (resp.status, elapsed_ms, None)
    except Exception as e:
        elapsed_ms = (time.perf_counter() - start) * 1000
        return (500, elapsed_ms, str(e))

def run_stress_test(total_requests=TOTAL_REQUESTS, concurrency=CONCURRENCY):
    print("=================================================================")
    print("    INICIANDO PRUEBA DE CARGA Y STRESS - CLUB DEPORTIVO MVC      ")
    print("=================================================================")
    print(f"[*] Destino: {BASE_URL}")
    print(f"[*] Total de peticiones: {total_requests}")
    print(f"[*] Concurrencia (hilos): {concurrency}")
    print("-----------------------------------------------------------------")

    opener = login_and_get_cookie()
    if not opener:
        print("[!] No se pudo establecer conexión con el servidor. ¿Está ejecutándose en el puerto 8080?")
        return

    print("[+] Autenticación Spring Security exitosa. Disparando solicitudes concurrentes...")

    latencies = []
    errors = 0
    status_codes = {}

    test_start = time.perf_counter()

    with ThreadPoolExecutor(max_workers=concurrency) as executor:
        futures = [executor.submit(execute_request, opener, i) for i in range(total_requests)]
        for f in as_completed(futures):
            status, ms, err = f.result()
            latencies.append(ms)
            status_codes[status] = status_codes.get(status, 0) + 1
            if status >= 400 or err is not None:
                errors += 1

    total_time = time.perf_counter() - test_start
    throughput = total_requests / total_time if total_time > 0 else 0

    latencies.sort()
    avg_lat = statistics.mean(latencies)
    median_lat = statistics.median(latencies)
    min_lat = min(latencies)
    max_lat = max(latencies)
    p90 = latencies[int(len(latencies) * 0.90)]
    p95 = latencies[int(len(latencies) * 0.95)]
    p99 = latencies[int(len(latencies) * 0.99)]

    print("\n=================================================================")
    print("                     RESULTADOS DEL TESTING                      ")
    print("=================================================================")
    print(f"Tiempo Total de Prueba:   {total_time:.2f} s")
    print(f"Throughput Alcanzado:     {throughput:.2f} req/s")
    print(f"Peticiones Exitosas:      {total_requests - errors} / {total_requests} ({( (total_requests-errors)/total_requests )*100:.1f}%)")
    print(f"Errores Registrados:      {errors} ({(errors/total_requests)*100:.2f}%)")
    print(f"Distribución HTTP Status: {status_codes}")
    print("-----------------------------------------------------------------")
    print("MÉTRICAS DE LATENCIA (TIEMPO DE RESPUESTA):")
    print(f"  Mínima:                 {min_lat:.2f} ms")
    print(f"  Promedio:               {avg_lat:.2f} ms")
    print(f"  Mediana (p50):          {median_lat:.2f} ms")
    print(f"  Percentil 90 (p90):     {p90:.2f} ms")
    print(f"  Percentil 95 (p95):     {p95:.2f} ms")
    print(f"  Percentil 99 (p99):     {p99:.2f} ms")
    print(f"  Máxima:                 {max_lat:.2f} ms")
    print("=================================================================")

    if errors == 0 and p95 < 500:
        print("[V] EVALUACIÓN: PRUEBA SUPERADA EXITOSAMENTE (Cumple SLAs de producción)")
    else:
        print("[!] EVALUACIÓN: SE DETECTARON LATENCIAS ALTAS O ERRORES")

if __name__ == "__main__":
    reqs = int(sys.argv[1]) if len(sys.argv) > 1 else TOTAL_REQUESTS
    conc = int(sys.argv[2]) if len(sys.argv) > 2 else CONCURRENCY
    run_stress_test(reqs, conc)

