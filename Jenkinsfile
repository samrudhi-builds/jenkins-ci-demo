pipeline {
    // Requires a Unix agent with java and javac on PATH.
    agent any

    stages {
        stage('Build') {
            steps {
                sh 'javac NumberAnalysis.java'
            }
        }

        stage('Test CLI and archive output') {
            steps {
                sh '''
                    set -eu
                    checks=$(mktemp -d)
                    trap 'rm -rf "$checks"' 0

                    check_console() {
                        number=$1
                        shift

                        java NumberAnalysis "$number" > "$checks/stdout"

                        # The final empty line matches the CLI's println output.
                        printf '%s\\n' "Input Number: $number" "$@" '' |
                            diff -u - "$checks/stdout"
                    }

                    check_console 5 \\
                        'Factorial = 120' \\
                        'SumOfDigitsOfFactorial = 3' \\
                        '5 is a Prime number' \\
                        'DigitsInFactorial = 3'

                    check_console 4 \\
                        'Factorial = 24' \\
                        'SumOfDigitsOfFactorial = 6' \\
                        '4 is NOT a Prime number' \\
                        'DigitsInFactorial = 2'

                    check_console 0 \\
                        'Factorial = 1' \\
                        'SumOfDigitsOfFactorial = 1' \\
                        '0 is NOT a Prime number' \\
                        'DigitsInFactorial = 1'

                    check_console -3 \\
                        'Factorial: Not defined for negative numbers' \\
                        '-3 is NOT a Prime number'

                    status=0
                    java NumberAnalysis abc > "$checks/stdout" 2> "$checks/stderr" || status=$?
                    test "$status" -eq 2
                    test ! -s "$checks/stdout"
                    printf '%s\\n' 'Invalid number: abc' | diff -u - "$checks/stderr"

                    status=0
                    java NumberAnalysis 1001 > "$checks/stdout" 2> "$checks/stderr" || status=$?
                    test "$status" -eq 2
                    test ! -s "$checks/stdout"
                    printf '%s\\n' 'Maximum factorial input is 1000. Received: 1001' |
                        diff -u - "$checks/stderr"

                    rm -f output.txt
                    java NumberAnalysis 7 output.txt > "$checks/stdout"

                    printf '%s\\n' \\
                        'Input Number: 7' \\
                        'Factorial = 5040' \\
                        'SumOfDigitsOfFactorial = 9' \\
                        '7 is a Prime number' \\
                        'DigitsInFactorial = 4' > "$checks/expected"

                    diff -u "$checks/expected" output.txt

                    {
                        cat "$checks/expected"
                        printf '\\n%s\\n' 'Results saved to output.txt'
                    } | diff -u - "$checks/stdout"
                '''

                archiveArtifacts artifacts: 'output.txt'
            }
        }
    }
}
