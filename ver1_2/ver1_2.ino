/*
Proyek "Garbage Reader"
Ver 1.2, 23 March 2014
By Emily
Serial Comm test [OK]
*/

int fsrPin = A0; //pin yang terhubung dengan fsr
int fsrReading; //bacaan analog
int fsrVoltage; //hasil convert ke tegangan
unsigned long fsrResistance; //hasil convert ke hambatan
unsigned long fsrConductance; //hasil convert ke konduktansi
double fsrForce; //hasil convert ke tekanan dalam Newton
float fsrMass;

void setup() {
  // put your setup code here, to run once:
  Serial.begin(9600); //inisialisasi komunikasi serial
}

void loop() {
  // put your main code here, to run repeatedly:
    
    fsrReading = analogRead(fsrPin);
  
    fsrVoltage = map(fsrReading, 0, 1023, 0, 3300); //due 3,3 volt, bacaan daam mV
  
    if(fsrVoltage == 0) ;
    else {
      fsrResistance = 3300 - fsrVoltage;
      fsrResistance *= 10000; //10k ohm
      fsrResistance /= fsrVoltage; //hitungan voltage divider
    
      fsrConductance = 1000000; //dalam microMho
      fsrConductance /= fsrResistance;
      
      //sesuai grafik FSR untuk pendekatan tekanan
      if (fsrConductance <= 1000) {
        fsrForce = fsrConductance / 80.0;
      }
      else {
        fsrForce = fsrConductance - 1000.0;
        fsrForce /= 30.0;
      }
      fsrMass = fsrForce / 9.8;
      byte* b = (byte*) &fsrMass;
      Serial.write(b[0]);
      Serial.write(b[1]);
      Serial.write(b[2]);
      Serial.write(b[3]);
    }
  delay(1000);
}
