package ai.watermelonbatch;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.NameTokenizers;
import org.springframework.batch.item.ItemProcessor;

public class CustomItemProcess implements ItemProcessor<Customer, Customer2> {

    private final ModelMapper modelMapper = new ModelMapper();

    @Override
    public Customer2 process(final Customer item) throws Exception {

        modelMapper.getConfiguration()
                   .setDestinationNameTokenizer(NameTokenizers.UNDERSCORE)
                   .setSourceNameTokenizer(NameTokenizers.UNDERSCORE);

        Customer2 customer2 = modelMapper.map(item, Customer2.class);

        return customer2;
    }
}
