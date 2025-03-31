GRADLE:=./gradlew

format:
	$(GRADLE) spotlessApply

format/check:
	$(GRADLE) spotlessCheck

clean:
	$(GRADLE) clean

build: clean test
	$(GRADLE) build -x test -x spotlessCheck

run: build
	$(GRADLE) bootRun -PmainClass=com.douglasmarq.password.simple_password.SimplePasswordApplication

test:
	$(GRADLE) build -x test
