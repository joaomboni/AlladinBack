from flask import Flask, render_template, request, jsonify
from flask_cors import CORS
from datetime import datetime, timedelta, timezone
import yfinance as yf
import pandas as pd

app = Flask(__name__)
CORS(app)
def calcular_indicadores(ticker):
    try:
        data_fim = datetime.now(timezone.utc)
        data_inicio = data_fim - timedelta(days=365)

        data_inicio_str = data_inicio.strftime('%Y-%m-%d')
        data_fim_str = data_fim.strftime('%Y-%m-%d')

        # Obter dados históricos
        dados = yf.download(ticker, start=data_inicio_str, end=data_fim_str)

        # Verifique se os dados estão disponíveis
        if dados.empty:
            raise ValueError("Dados históricos vazios para o ticker fornecido.")

        # Obter o preço atual (o último preço no conjunto de dados)
        precoTela = dados['Close'].iloc[-1] if not dados['Close'].empty else None

        # Certifique-se de que precoTela é um número
        if isinstance(precoTela, (pd.Series, pd.DataFrame)):
            precoTela = precoTela.values[0]  # Extrai o valor escalar da série

        # Obter o Lucro por Ação (LPA)
        lpa = yf.Ticker(ticker).info.get('trailingEps', None)

        # Obter o Valor Patrimonial por Ação (VPA)
        patrimonio_liquido = yf.Ticker(ticker).info.get('bookValue', None)

        # Obter dividendos pagos nos últimos 12 meses
        dividendos_12_meses = yf.Ticker(ticker).dividends

        if not dividendos_12_meses.empty:
            dividendos_12_meses = dividendos_12_meses.loc[data_inicio:data_fim]
            dividendos_12_meses_total = dividendos_12_meses.sum()

            # Calcular dividendYield apenas se precoTela não for None
            if precoTela is not None and precoTela != 0:
                dividendYield = (dividendos_12_meses_total / precoTela) * 100
            else:
                dividendYield = None
        else:
            dividendYield = None

        #DEBUG
        print({
        "ticker": ticker,
        "precoTela": precoTela,
        "lpa": lpa,
        "vpa": patrimonio_liquido,
        "dividendYield": dividendYield
    })
        
        return {
            "ticker": ticker,
            "precoTela": float(precoTela) if isinstance(precoTela, (int, float)) else None,
            "lpa": float(lpa) if isinstance(lpa, (int, float)) else None,
            "vpa": float(patrimonio_liquido) if isinstance(patrimonio_liquido, (int, float)) else None,
            "dividendYield": dividendYield
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

     # Adicionar ".SA" ao final do ticker
    #ticker_fornecido = ticker_fornecido + ".SA"
    
    resultados = calcular_indicadores(ticker_fornecido)

    if resultados is not None:
        return jsonify(resultados)
    else:
        return jsonify({"error": f"Não foi possível obter informações para {ticker_fornecido}"}), 500

if __name__ == '__main__':
    app.run(debug=True)