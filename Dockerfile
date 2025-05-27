# build
FROM ghcr.io/graalvm/graalvm-ce:latest as builder

RUN gu install native-image

WORKDIR /app

COPY . .

RUN ./gradlew -Pnative -DskipTests package

# runtime
FROM alpine:3.19

RUN apk add --no-cache libc6-compat

WORKDIR /app

COPY --from=builder /app/target/wishlist-api .

EXPOSE 8080
ENTRYPOINT ["./wishlist-api"]
