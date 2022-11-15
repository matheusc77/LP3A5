package com.gildedrose;

class GildedRose {
   Item[] items;
   private static final String BACKSTAGE_PASSES = "Backstage passes to a TAFKAL80ETC concert";
   private static final String SULFURAS = "Sulfuras, Hand of Ragnaros";
   private static final String AGED_BRIE = "Aged Brie";
   private static final String CONJURED = "Conjured Mana Cake";
   int adjustment = 1;
   

   public GildedRose(Item[] items) {
      this.items = items;
   }


   public void updateQuality() {
      for (Item item : items) {
         updateItemQuality(item);
      }
   }

   private void updateItemQuality(Item item) {
      boolean isExpired = item.sellIn < 1;
      int degradeRate = determineDegradeRate(item, isExpired);
      boolean doesDegrade = !item.name.equals(AGED_BRIE) && !item.name.equals(BACKSTAGE_PASSES) && !item.name.equals(SULFURAS);

      if (doesDegrade) {
         adjustQuality(item, degradeRate);
      } 
      
      if(item.name.equals(AGED_BRIE)){
         adjustQuality(item, adjustment);
      }

      if (item.name.equals(BACKSTAGE_PASSES)) {
         updateBackstagePassQuality(item, isExpired);
      }

      if (!item.name.equals(SULFURAS)) {
         item.sellIn = item.sellIn - adjustment;
      }

      if (isExpired && item.name.equals(AGED_BRIE)) {
         adjustQuality(item, adjustment);
      }
   }


   private void updateBackstagePassQuality(Item item, boolean isExpired) {
      adjustQuality(item, adjustment);
      if (item.sellIn < 11) {
         adjustQuality(item, adjustment);
      }

      if (item.sellIn < 6) {
         adjustQuality(item, adjustment);
      }

      if (isExpired) {
         item.quality = item.quality - item.quality;
      }
   }

   private int determineDegradeRate(Item item, boolean isExpired) {
      int baseDegradeRate = item.name.equals(CONJURED) ? -2 : -1;
      return isExpired ? 2*baseDegradeRate : baseDegradeRate;
   }


   private void adjustQuality(Item item, int adjustment) {
      int newQuality = item.quality + adjustment;
      if(newQuality <= 50 && newQuality >= 0){
         item.quality = newQuality;
      }
   }
}