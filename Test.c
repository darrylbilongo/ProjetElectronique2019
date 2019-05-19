#include <18F458.h>
#device ADC=10
#use delay(crystal=20000000)
#use rs232(baud=9600,parity=N,xmit=PIN_C6,rcv=PIN_C7,bits=8,stream=PORT1)

void gestionLed(int temp, int seuil);
void affTemp(int temperature);
void main()
{
   setup_adc_ports(AN0);
   setup_adc(ADC_CLOCK_INTERNAL);
   setup_low_volt_detect(FALSE);
   
   
   int seuil = 35;
   int temperature = 10;

   while(TRUE)
   { 
	  temperature = read_adc() * 100 / 1024;
      gestionLed(temperature,seuil); 
      affTemp(temperature);
    
      if(temperature > seuil){
         printf("%c", temperature | (1 >> 7));
      }
      else{
         printf("%c", temperature | (0 >> 7));
      }

      //delay_ms(500);
   }
}

void gestionLed(int temp, int seuil){
     if(temp > seuil){
      output_bit(PIN_C1,1);
      output_bit(PIN_C0,0);
      
   }
   else{
      output_bit(PIN_C1,0);
      output_bit(PIN_C0,1);
   }

}
void affTemp(int temperature){
      output_d((temperature % 10) | (1 << 4));
      delay_ms(7);
	  printf(temperature);
      output_d((temperature / 10) | (1 << 5));
      delay_ms(7);
	  printf(temperature);
}



