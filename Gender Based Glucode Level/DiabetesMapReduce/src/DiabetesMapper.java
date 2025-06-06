import java.io.IOException;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class DiabetesMapper extends Mapper<LongWritable, Text, Text, IntWritable> {
    private static final IntWritable one = new IntWritable(1);
    private Text compositeKey = new Text();

    @Override
    protected void map(LongWritable key, Text value, Context context)
            throws IOException, InterruptedException {
        // Skip header row
        if (key.get() == 0 && value.toString().toLowerCase().contains("gender")) return;

        String[] fields = value.toString().split(",");
        if (fields.length >= 15) {
            try {
                String gender = fields[1].trim();     // Gender (2nd column)
                String diabetics = fields[14].trim(); // Diabetics (15th column)

                compositeKey.set(gender + "," + diabetics); // E.g., "Female,0.672"
                context.write(compositeKey, one);
            } catch (Exception e) {
                // Log or skip malformed line
            }
        }
    }
}
