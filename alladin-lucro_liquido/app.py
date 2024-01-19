from flask import Flask, jsonify
import yfinance as yf
import pandas as pd
from datetime import datetime, timedelta, timezone
import numpy as np

app = Flask(__name__)

@app.route('/lucro_liquido/<ticker>', methods=['GET'])
def obter_lucro_liquido(ticker):
    try:
        # Obtém a data atual no fuso horário UTC.
        now = datetime.now(timezone.utc)

        # Obtém os dados financeiros do ticker.
        empresa = yf.Ticker(ticker)
        data = empresa.financials

        # Remove o primeiro elemento do índice (se for um texto não-data)
        if data.index[0] == 'Tax Rate For Calcs':
            data = data.iloc[1:]

        # Converte o índice para o tipo datetime, ignorando erros.
        data.index = pd.to_datetime(data.index, errors='coerce')

        # Filtra os dados para os últimos 6 anos.
        data = data.loc[data.index >= np.datetime64(now - timedelta(days=365.25 * 6))]

        # Procura dinamicamente pela coluna 'netIncome'.
        lucro_liquido_column = None
        for column in data.columns:
            if 'netIncome' in column:
                lucro_liquido_column = column
                break

        # Verifica se a coluna 'netIncome' foi encontrada.
        if lucro_liquido_column:
            # Obtém o lucro líquido mais recente.
            lucro_liquido = data[lucro_liquido_column].iloc[0]

            # Retorna o resultado como JSON.
            resultado = {
                'ticker': ticker,
                'lucro_liquido': lucro_liquido
            }
            return jsonify(resultado)
        else:
            raise ValueError(f"A coluna 'netIncome' não foi encontrada nos dados do ticker {ticker}.")

    except Exception as e:
        return jsonify({'erro': str(e)}), 500

if __name__ == '__main__':
    app.run(debug=True)