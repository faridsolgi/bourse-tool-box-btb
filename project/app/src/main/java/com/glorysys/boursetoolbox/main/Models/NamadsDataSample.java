package com.glorysys.boursetoolbox.main.Models;

public class NamadsDataSample {

    private String NamadName;
    private String NamadCompanyName;
    private long Amount;
    private long Volume;
    private long Value;
    private long Yesterday;
    private long FirstPrice;
    private long LastDealPriceAmount;
    private long LastDealChangePrice;
    private double LastDealPercentage;
    private long LastPriceAmount;
    private long LastPriceChange;
    private double LastPricePercentage;
    private long LowestPrice;
    private long HighestPrice;
    private String EPS;
    private String PE;

    public String getBuy() {
        return buy;
    }

    public void setBuy(String buy) {
        this.buy = buy;
    }

    public String getSell() {
        return sell;
    }

    public void setSell(String sell) {
        this.sell = sell;
    }

    private String buy;
    private String sell;


    public long getVolume() {
        return Volume;
    }

    public void setVolume(long volume) {
        Volume = volume;
    }


    public String getNamadName() {
        return NamadName;
    }

    public void setNamadName(String namadName) {
        NamadName = namadName;
    }

    public String getNamadCompanyName() {
        return NamadCompanyName;
    }

    public void setNamadCompanyName(String namadCompanyName) {
        NamadCompanyName = namadCompanyName;
    }

    public long getAmount() {
        return Amount;
    }

    public void setAmount(long amount) {
        Amount = amount;
    }

    public long getValue() {
        return Value;
    }

    public void setValue(long value) {
        Value = value;
    }

    public long getYesterday() {
        return Yesterday;
    }

    public void setYesterday(long yesterday) {
        Yesterday = yesterday;
    }

    public long getFirstPrice() {
        return FirstPrice;
    }

    public void setFirstPrice(long firstPrice) {
        FirstPrice = firstPrice;
    }

    public long getLastDealPriceAmount() {
        return LastDealPriceAmount;
    }

    public void setLastDealPriceAmount(long lastDealPriceAmount) {
        LastDealPriceAmount = lastDealPriceAmount;
    }

    public long getLastDealChangePrice() {
        return LastDealChangePrice;
    }

    public void setLastDealChangePrice(long lastDealChangePrice) {
        LastDealChangePrice = lastDealChangePrice;
    }

    public double getLastDealPercentage() {
        return LastDealPercentage;
    }

    public void setLastDealPercentage(double lastDealPercentage) {
        LastDealPercentage = lastDealPercentage;
    }

    public long getLastPriceAmount() {
        return LastPriceAmount;
    }

    public void setLastPriceAmount(long lastPriceAmount) {
        LastPriceAmount = lastPriceAmount;
    }

    public long getLastPriceChange() {
        return LastPriceChange;
    }

    public void setLastPriceChange(long lastPriceChange) {
        LastPriceChange = lastPriceChange;
    }

    public double getLastPricePercentage() {
        return LastPricePercentage;
    }

    public void setLastPricePercentage(double lastPricePercentage) {
        LastPricePercentage = lastPricePercentage;
    }

    public long getLowestPrice() {
        return LowestPrice;
    }

    public void setLowestPrice(long lowestPrice) {
        LowestPrice = lowestPrice;
    }

    public long getHighestPrice() {
        return HighestPrice;
    }

    public void setHighestPrice(long highestPrice) {
        HighestPrice = highestPrice;
    }

    public String getEPS() {
        return EPS;
    }

    public void setEPS(String EPS) {
        this.EPS = EPS;
    }

    public String getPE() {
        return PE;
    }

    public void setPE(String PE) {
        this.PE = PE;
    }



}
