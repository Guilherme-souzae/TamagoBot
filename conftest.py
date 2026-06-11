# conftest.py (raiz) — já está correto de localização
import sys, os
sys.path.insert(0, os.path.join(os.path.dirname(__file__), "src"))