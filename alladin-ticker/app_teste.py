from flask import Flask, render_template, request, jsonify
from flask_cors import CORS
from datetime import datetime, timedelta, timezone
import yfinance as yf

app = Flask(__name__)
CORS(app)

def calcular_indicadores(ticker):
    try:
        # Calcular a data de início como sendo 12 meses atrás da data atual
        data_fim = datetime.now(timezone.utc)
        data_inicio = data_fim - timedelta(days=365)

        # Converter as datas para o formato necessário pelo yfinance
        data_inicio_str = data_inicio.strftime('%Y-%m-%d')
        data_fim_str = data_fim.strftime('%Y-%m-%d')

        # Obter dados históricos
        dados = yf.download(ticker, start=data_inicio_str, end=data_fim_str)

        # Obter o preço atual (o último preço no conjunto de dados)
        precoTela = dados['Close'].iloc[-1] if not dados.empty else None
        if precoTela is None:
            print("Erro: Preço não encontrado.")
            return None

        # Obter o Lucro por Ação (LPA)
        lpa = yf.Ticker(ticker).info.get('trailingEps', None)

        # Obter o Valor Patrimonial por Ação (VPA)
        patrimonio_liquido = yf.Ticker(ticker).info.get('bookValue', None)

        # Debug para verificar os valores de precoTela e dividendos_12_meses_total
        print("DEPURAÇÃO")
        print(f"Preço da ação: {precoTela}")

        # Obter dividendos pagos nos últimos 12 meses
        dividendos_12_meses = yf.Ticker(ticker).dividends

        # Verificar se a série de dividendos não está vazia
        if dividendos_12_meses.empty:
            print("Nenhum dividendo encontrado para os últimos 12 meses.")
            dividendos_12_meses_total = 0
        else:
            # Filtrar dividendos entre a data de início e fim
            dividendos_12_meses = dividendos_12_meses[
                (dividendos_12_meses.index >= data_inicio) &
                (dividendos_12_meses.index <= data_fim)
            ]
            
            # Verificar se a série filtrada não está vazia
            if dividendos_12_meses.empty:
                print("Nenhum dividendo encontrado após o filtro de datas.")
                dividendos_12_meses_total = 0
            else:
                # Calcular o total de dividendos pagos
                dividendos_12_meses_total = dividendos_12_meses.sum()

        print(f"Total de dividendos pagos nos últimos 12 meses: {dividendos_12_meses_total}")

        # Calcular o Dividend Yield
        if precoTela is not None and precoTela != 0 and dividendos_12_meses_total > 0:
            dividendYield = (dividendos_12_meses_total / precoTela) * 100
        else:
            dividendYield = None
            print(f"Erro: Dividendos não encontrados ou são zero.")

        return {
            "ticker": ticker,
            "precoTela": float(precoTela) if precoTela is not None else None,
            "lpa": float(lpa) if lpa is not None else None,
            "vpa": float(patrimonio_liquido) if patrimonio_liquido is not None else None,
            "dividendYield": dividendYield  # Não converter aqui, já foi tratado
        }

    except Exception as e:
        print(f"Erro ao obter dados para {ticker}: {e}")
        return None

@app.route('/var/www/html/alladinfront/formulariov3.html')
def index():
    return render_template('formulariov3.html')

@app.route('/buscar')
def buscar():
    # Obter o ticker fornecido como parâmetro da consulta
    ticker_fornecido = request.args.get('ticker')

    if not ticker_fornecido:
        return jsonify({"error": "Parâmetro 'ticker' não fornecido"}), 400

    # Adicionar ".SA" ao final do ticker (se necessário)
    # ticker_fornecido = ticker_fornecido + ".SA"

    # Chamar a função para calcular os indicadores
    resultados = calcular_indicadores(ticker_fornecido)

    if resultados is not None:
        return jsonify(resultados)
    else:
        return jsonify({"error": f"Não foi possível obter informações para {ticker_fornecido}"}), 500

if __name__ == '__main__':
    app.run(debug=True)