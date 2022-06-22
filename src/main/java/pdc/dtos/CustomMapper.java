package pdc.dtos;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.TypeMap;
import org.modelmapper.convention.MatchingStrategies;
import pdc.model.Failure;

public class CustomMapper {
    public ModelMapper createModelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
//        TypeMap<Object, Object> typeMap = modelMapper.createTypeMap(Failure, FailureDto);
//        typeMap.addMapping((Failure src) -> src.getProdauftrag().getFirmaId(), FailureDto::setFirmaId)
//        typeMap.addMapping((Failure src) -> src.getPersonal().getPersonalId(), FailureDto::setPersonalId);
//        modelMapper.addMappings(new PropertyMap<Failure, FailureDto>() {
//            @Override
//            protected void configure() {
//                map().setPersonalId(source.getPersonalId());
//                map().setSchichtplangruppeId(source.getSchichtplangruppe().getSchichtplangruppeId());
//                map().setProdstufeId(source.getProdauftrag().getProdstufeId());
//                map().setPaNrId(source.getProdauftrag().getPaNrId());
//                map().setFirmaId(source.getProdauftrag().getFirmaId());
//                map().setAbfallId(source.getAbfallcode().getAbfallId());
//            }
//        });
//        modelMapper.typeMap(Failure.class, FailureDto.class).addMappings(mapper -> {
//            mapper.map(src -> src.getProdauftrag().getFirmaId(),
//                    FailureDto::setFirmaId);
//            mapper.map(src -> src.getProdauftrag().getProdstufeId(),
//                    FailureDto::setProdstufeId);
//        });
//        modelMapper.validate();
//        modelMapper.getConfiguration().setAmbiguityIgnored(true);
        return modelMapper;
    }
}
