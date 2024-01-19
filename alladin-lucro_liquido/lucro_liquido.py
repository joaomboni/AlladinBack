import yfinance as yf

def obter_lucro_liquido(ticker):
    try:
        # Obtém os dados detalhados do ticker.
        empresa = yf.Ticker(ticker)
        detalhes = empresa.get_quote_table(proxy="PROXY_SERVER")

        # Procura pelo lucro líquido nos detalhes.
        if 'Net Income' in detalhes:
            lucro_liquido = detalhes['Net Income']
            return lucro_liquido
        else:
            raise ValueError(f"Lucro líquido não encontrado nos detalhes do ticker {ticker}.")

    except Exception as e:
        return {'erro': str(e)}

# Substitua 'AAPL' pelo ticker da empresa desejada.
ticker = 'AAPL'
lucro_liquido = obter_lucro_liquido(ticker)

if 'erro' in lucro_liquido:
    print(f"Erro: {lucro_liquido['erro']}")
else:
    print(f"Lucro líquido da empresa {ticker}: {lucro_liquido}")