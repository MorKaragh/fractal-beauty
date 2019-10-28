import pygame
from random import randrange as rnd

DIMENSIONS = 3

SIZE = 1000

DIVISION_FACTOR = 2


def run_fractal(dimensions):
    pygame.init()
    pygame.display.set_caption("chaotic beauty")
    screen = pygame.display.set_mode((SIZE, SIZE))

    points = []
    for i in range(0, dimensions + 1):
        points.append([rnd(1, SIZE), rnd(1, SIZE)])

    calculated_points = []

    while True:
        screen.fill((255, 255, 255))

        for p in points:
            pygame.draw.circle(screen, (1, 1, 1), [p[0], p[1]], 2)

        for p in calculated_points:
            pygame.draw.circle(screen, (1, 1, 1), [p[0], p[1]], 2)

        destination_point = points[rnd(1, dimensions + 1)]
        move_to = get_middle_point(points[0], destination_point)

        calculated_points.append(points[0])
        points[0] = move_to

        pygame.display.flip()


def get_middle_point(acting_point, destination_point):
    x_c = int(min(acting_point[0], destination_point[0]) + (
            max(acting_point[0], destination_point[0]) - min(acting_point[0], destination_point[0])) / DIVISION_FACTOR)
    y_c = int(min(acting_point[1], destination_point[1]) + (
            max(acting_point[1], destination_point[1]) - min(acting_point[1], destination_point[1])) / DIVISION_FACTOR)
    move_to = [x_c, y_c]
    return move_to


run_fractal(DIMENSIONS)
