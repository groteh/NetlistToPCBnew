/**
 * Write a description of class Components here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
import java.util.TreeMap;
public class Components
{
    public TreeMap<String,String> componentTree = new TreeMap<String,String>();
    public Components()
    {
        componentTree.put("resistor", "      (footprint Resistor_SMD:R_0603_1608Metric_Pad1.05x0.95mm_HandSolder)" + NetlistToPCBnew.lineSep + "      (datasheet ~)" + NetlistToPCBnew.lineSep + "      (libsource (lib Device) (part R) (description Resistor))");
        componentTree.put("capacitor", "      (footprint Capacitor_SMD:C_0805_2012Metric)" + NetlistToPCBnew.lineSep + "      (datasheet ~)" + NetlistToPCBnew.lineSep + "      (libsource (lib Device) (part C) (description \"Unpolarized capacitor\"))");
        componentTree.put("inductor", "      (footprint Inductor_SMD:L_0805_2012Metric)" + NetlistToPCBnew.lineSep + "      (datasheet ~)" + NetlistToPCBnew.lineSep + "      (libsource (lib Device) (part L) (description Inductor))");
        componentTree.put("LM324", "      (footprint Package_SO:TSSOP-14_4.4x5mm_P0.65mm)" + NetlistToPCBnew.lineSep + "      (datasheet http://www.ti.com/lit/ds/symlink/lm2902-n.pdf)" + NetlistToPCBnew.lineSep + "      (libsource (lib Amplifier_Operational) (part LM324) (description \"Low-Power, Quad-Operational Amplifiers, DIP-14/SOIC-14/SSOP-14\"))");
        componentTree.put("LM2901", "      (footprint Package_DIP:DIP-14_W7.62mm)" + NetlistToPCBnew.lineSep + "      (datasheet https://www.st.com/resource/en/datasheet/lm2901.pdf)" + NetlistToPCBnew.lineSep + "      (libsource (lib Comparator) (part LM2901) (description \"Quad Differential Comparators, DIP-14/SOIC-14/TSSOP-14\"))");
        componentTree.put("LM319", "      (footprint Package_SO:SOIC-14_3.9x8.7mm_P1.27mm)"  + NetlistToPCBnew.lineSep + "      (datasheet http://www.ti.com/lit/ds/symlink/lm319-n.pdf)"  + NetlistToPCBnew.lineSep + "      (libsource (lib Comparator) (part LM319) (description \"High Speed Dual Comparator, DIP-14/SOIC-14\"))");
        componentTree.put("NE555", "      (footprint Package_SO:SOIC-8_3.9x4.9mm_P1.27mm)"  + NetlistToPCBnew.lineSep + "      (datasheet http://www.ti.com/lit/ds/symlink/ne555.pdf)"  + NetlistToPCBnew.lineSep + "      (libsource (lib Timer) (part NE555D) (description \"Precision Timers, 555 compatible, SOIC-8\"))");
        componentTree.put("MCP6004", "      (footprint Package_SO:SOIC-14_3.9x8.7mm_P1.27mm)"  + NetlistToPCBnew.lineSep + "      (datasheet http://ww1.microchip.com/downloads/en/DeviceDoc/21733j.pdf)"  + NetlistToPCBnew.lineSep + "      (libsource (lib Amplifier_Operational) (part MCP6004) (description \"1MHz, Low-Power Op Amp, DIP-14/SOIC-14/TSSOP-14\"))");
        componentTree.put("BS170", "      (footprint Package_TO_SOT_THT:TO-92_Inline)"  + NetlistToPCBnew.lineSep + "      (datasheet http://www.fairchildsemi.com/ds/BS/BS170.pdf)"  + NetlistToPCBnew.lineSep + "      (libsource (lib Transistor_FET) (part BS170) (description \"0.5A Id, 60V Vds, N-Channel MOSFET, TO-92\"))");
        componentTree.put("ZVN3306", "      (footprint Package_TO_SOT_SMD:SOT-23)"  + NetlistToPCBnew.lineSep + "      (datasheet http://www.diodes.com/assets/Datasheets/ZVN3306F.pdf)"  + NetlistToPCBnew.lineSep + "      (libsource (lib Transistor_FET) (part ZVN3306F) (description \"0.15A Id, 60V Vds, N-Channel MOSFET, SOT-23\"))");
        componentTree.put("battery", "      (footprint Battery:BatteryHolder_Keystone_2460_1xAA)" + NetlistToPCBnew.lineSep + "      (datasheet ~)" + NetlistToPCBnew.lineSep + "      (libsource (lib Device) (part Battery) (description \"Multiple-cell battery\"))");
    }
    
    
    public static void main (){
        Components c = new Components();
        //c.map.put("inductor","TrmpisDumb");
        //c.map.replace("inductor","damn");
        System.out.println(c.componentTree.get("inductor"));
    }
}
