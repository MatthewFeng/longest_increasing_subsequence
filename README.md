# longest_increasing_subsequence

this code follows the website :https://www.hiredintech.com/classrooms/algorithm-design/lesson/12/task/14/solution

and give a solution with O(nlogn) complexity


-----------------------------------------------------------------------------------------------


Given a list of N integers find the longest increasing subsequence in this list.

Example

If the list is [16, 3, 5, 19, 10, 14, 12, 0, 15] one possible answer is the subsequence [3, 5, 10, 12, 15], another is [3, 5, 10, 14, 15].

If the list has only one integer, for example: [14], the correct answer is [14].

One more example: [10, 8, 6, 4, 2, 0], a possible correct answer is [8].


Solution: Longest increasing subsequence
This problem has at least two different solutions using dynamic programming. The first one that we will look at is slower and if you implement it for this practice task your solution will most likely not pass all the test cases.

Slower solution - O(N^2)
For the first solution our problem will be to find the longest increasing subsequence of the sequence S ending with the number S[i] in position i - F(i). Once we have the answer for all possible last numbers we will be able to take the best answer. How do we compute the answer for a given number at position i, though?

If we want to compute F(i) we can loop through all numbers to the left of S[i] and consider the ones, which are smaller than it. We are only interested in the smaller ones because we will be looking for possible subsequences to which we can append S[i]. We can take the one, which is the longest and add 1 to its length to form a new sub-sequence ending in position i. This will be the answer for F[i]. We could iterate from left to right, computing the values for F[i]. This way we will be sure that all values of F to the left of where we are are always computed.

The base case is for the left-most number. For it we know that the best answer is 1, the subsequence consisting of just this number.

For each number in position i once we compute F[i] we will store the position of the previous number in the sequence, so that it is possible to restore the whole sequence if needed for the final answer.

Here is pseudocode showing this idea:

# S holds the numbers, it's length is L
# P holds the index of the previous number

F[1] = 1
P[1] = -1
best_index = 1

for i = 2 to L
  F[i] = 1
  P[i] = -1
  for j = 1 to i-1
    if S[j] < S[i] and F[j] + 1 > F[i]
      F[i] = F[j] + 1
      P[i] = j
      if F[best_index] < F[i]
        best_index = i
      end
    end
  end
end

answer = []
index = best_index
while index != -1
  answer.append(S[index])
  index = P[index]
end

print answer.reversed
As you can see this solution is quadratic in terms of the number of elements in the input sequence. Given that in some test cases there may be up to 2,000,000 numbers, such time complexity may not be good enough to compute the answer in time.

At an interview it may be ok to code this slower solution, it depends on what the interviewer's goal is. If the constraints given as low enough, you may be ok with coding this solution but you probably first need to check with the interviewer if that is good enough.

Faster solution - O(NlogN)
As mentioned above there is another faster solution to this problem, again using dynamic programming but also taking advantage of one interesting observation.

We again process the numbers in the sequence S going from left to right - S[1], S[2], etc. After processing each element the algorithm will maintain two arrays of values:

M which holds in M[i] the index of the smallest number from S, where there is an increasing subsequence with length i ending with the number S[M[i]].
P which holds in P[j] the index of the predecessor to the number S[j] in the longest increasing subsequence ending in S[j].
Given these two arrays, we can observe that the numbers referenced in M will always be in increasing order. For each new number from S that we process, we should check if there is a value in M that can be replaced with the new number. This would mean that we have found a smaller number to be the last number in a subsequence with a given length.

For example, if M referenced the numbers [3, 6, 7, 10] at a given point in time and we're currently processing the number 5. This means that so far we've found that the smallest last number to end a subsequence of length 1 with is 3. The smallest number to end a subsequence with length 2 is 6 and so on. The current number, 5, falls between 3 and 6. This means that if we attach 5 to the subsequence with length 1, ending with 3, we will obtain a subsequence with length 2, which ends with 5. This is better than what we have so far - 6. Thus, we should update M[2] to point to this new number.

Now comes the time to take advantage of the constant increasing ordering of M. We can find the position of each new number using binary search instead of going through all values in M. This means that for each number that we process we will only have O(logN) steps to find its proper place. That is why the whole algorithm has time complexity of O(NlogN).

In order to restore the longest increasing subsequence we need to look at the right-most value in M, it will show us the smallest number with which we can end a subsequence with the biggest possible length. Since we will also store the index of the previous number in P, it will be possible to go back and restore the whole sequence.

Finally, let's go through an example and see how M and P change, in order to illustrate the algorithm. The input sequence is 4, 2, 3, 12, 11.

Step 1: 4

Longest increasing subsequence length	1
M	4


Index in S	1
P	-1
Step 2: 2

Longest increasing subsequence length	1
M	2


Index in S	1	2
P	-1	-1
Step 3: 3

Longest increasing subsequence length	1	2
M	2	3


Index in S	1	2	3
P	-1	-1	2
Step 4: 12

Longest increasing subsequence length	1	2	3
M	2	3	4


Index in S	1	2	3	4
P	-1	-1	2	3
Step 5: 11

Longest increasing subsequence length	1	2	3
M	2	3	5


Index in S	1	2	3	4	5
P	-1	-1	2	3	3


The longest increasing subsequence has length 3 and the smallest number that is an end to such a sequence is 11, at position 5 in S. Its predecessor is the number at position 3 - the number 3, before it comes the number in position 2 - the number 2, and this is the whole subsequence.