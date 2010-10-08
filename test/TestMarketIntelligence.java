class TestMarketIntelligence
{
    public static void main(String args[]) throws InterruptedException
    {
        MarketIntelligence mi = new MarketIntelligence();
        while( true ){
             String ask = mi.getAsk("0001");
             System.out.println(ask);
             Thread.sleep(1000);
        }
    }
}