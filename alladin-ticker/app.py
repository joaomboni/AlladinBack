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
        precoTela = dados['Close'].iloc[-1]

        # Obter o Lucro por Ação (LPA)
        lpa = yf.Ticker(ticker).info['trailingEps']

        # Comentar para entender melhor como calular
        # Obter o Valor Patrimonial por Ação (VPA)
        patrimonio_liquido = yf.Ticker(ticker).info['bookValue']
        #shares_outstanding = yf.Ticker(ticker).info['sharesOutstanding']
        #if patrimonio_liquido is not None and shares_outstanding is not None:
            #vpa = patrimonio_liquido / shares_outstanding
        #else:
            #vpa = None

        # Imprime Teste
        #print(f"Patrimônio Líquido: {patrimonio_liquido}")
        #print(f"Ações em Circulação: {shares_outstanding}")

        # Obter dividendos pagos nos últimos 12 meses em PORCENTAGEM
        dividendos_12_meses = yf.Ticker(ticker).dividends

        if not dividendos_12_meses.empty:
            dividendos_12_meses = dividendos_12_meses.loc[data_inicio:data_fim]
            dividendos_12_meses_total = dividendos_12_meses.sum()
            dividendYield = (dividendos_12_meses_total / precoTela) * 100 if precoTela != 0 else None
        else:
            dividendYield = None
        
        return {
            "ticker": ticker,
            "precoTela": precoTela,
            "lpa": lpa,
            "vpa": patrimonio_liquido,
            #"vpa": vpa,
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