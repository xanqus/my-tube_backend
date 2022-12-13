package com.xaqnus.my_tube_backend.video.dao;

import com.xaqnus.my_tube_backend.video.domain.VideoDocument;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface VideoSearchRepository extends ElasticsearchRepository<VideoDocument, Long> {
    @Query(" {\n" +
            "\n" +
            "    \"bool\": {\n" +
            "\n" +
            "      \"must\": {\n" +
            "\n" +
            "        \"dis_max\": {\n" +
            "\n" +
            "          \"queries\": [{ \"match\": { \"title_nori\": \"?0\" }},\n" +
            "        { \"match\": { \"description_nori\": \"?1\" }}],\n" +
            "        \"tie_breaker\": 0.7\n" +
            "\n" +
            "          }\n" +
            "\n" +
            "      }\n" +
            "\n" +
            "    }\n" +
            "\n" +
            "}," +
            "   \"sort\":[\n" +
            "      {\n" +
            "         \"updated_date\":{\n" +
            "            \"order\":\"asc\", \"format\": \"strict_date_optional_time_nanos\"" +
            "         }\n" +
            "      }\n" +
            "   ]\n"
            )
    List<VideoDocument> findByNameOrDisplayNameOrDescription(String title, String description);
}
