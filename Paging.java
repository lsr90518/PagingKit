import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Paging {

    @ApiModelProperty(value = "offset", notes = "省略すると最初からです。")
    private Long offset;
    @ApiModelProperty(value = "取得件数", notes = "省略すると10件、100件以上入れても内部で100に変換されます。")
    private Integer limit;
}
