package DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductDTO {

    private String id;

    private String name;

    private String description;

    public static class Builder {

        private String id;
        private String name;
        private String description;

        public Builder setDescription(String description) {
            this.description = description;
            return this;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setId(String id) {
            this.id = id;
            return this;
        }

        public ProductDTO build() {
            return new ProductDTO(this);
        }


    }

    private ProductDTO(Builder builder){
        this.description = builder.description;
        this.id = builder.id;
        this.name = builder.name;
    }


}
