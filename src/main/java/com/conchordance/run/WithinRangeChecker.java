package com.conchordance.run;

import org.apache.commons.lang3.ArrayUtils;

import java.util.Arrays;
import java.util.Collections;

public class WithinRangeChecker {


   public static boolean isWithinRange(int[] frets, int numberOfFrets) {

      int min = getMin(frets);
      int max = getMax(frets);

      if ((max - min) <= numberOfFrets) {
         return true;
      } else {
         return false;
      }
   }

   private static int getMin(int[] frets) {

      int min = Integer.MAX_VALUE;

      for (int i = 0; i < frets.length; i++) {

         if (frets[i] == -1) {
            continue;
         }

         if (frets[i] < min) {
            min = frets[i];
         }
      }

      return min;
   }

   private static int getMax(int[] frets) {

      return Collections.max(Arrays.asList(ArrayUtils.toObject(frets)));
   }
}
